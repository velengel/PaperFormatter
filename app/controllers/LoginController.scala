package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models._
import play.api.data.validation.Valid
import play.api.data.validation.Constraint
import play.api.data.validation.Invalid
import play.api.i18n.I18nSupport

@Singleton
class LoginController @Inject()(cc: ControllerComponents) extends AbstractController(cc) with I18nSupport {

  // ログインフォーム
  // 空白チェックにオリジナルメッセージを設定してみる
  val loginUserForm = Form( mapping("elem0" -> text.verifying("リンクは大事！" , {!_.isEmpty()}) , 
                                    "elem1" -> text.verifying("ひとことでいうと......" , {!_.isEmpty()}) , 
                                    "elem2" -> text.verifying("新規性？アップデート？" , {!_.isEmpty()}),
                                    "elem3" -> text.verifying("惹かれたところとか" , {!_.isEmpty()}) , 
                                    "elem4" -> text.verifying("実験？アンケート？サーベイ？" , {!_.isEmpty()}),
                                    "elem5" -> text.verifying("今後の展望とか" , {!_.isEmpty()}) , 
                                    "elem6" -> text.verifying("自分への宿題" , {!_.isEmpty()})
                                    )
                                    (LoginUser.apply)(LoginUser.unapply) 
                           )

  // ログイン画面の初期表示
  def loginInit() = Action { implicit request =>
     Ok( views.html.login(loginUserForm) )
  }

  // ログイン画面のSubmit
  def loginSubmit() = Action { implicit request: Request[AnyContent] =>
     loginUserForm.bindFromRequest.fold(
         // 入力チェックNG
         errors => {           
           BadRequest( views.html.login(errors) )
         },
         // 入力チェックOK
         success => {
           val loginUser = loginUserForm.bindFromRequest.get
           Ok( views.html.loginSuccess(loginUser) )
         }        
     )
  }

}