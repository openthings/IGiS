package igis.app.controllers

import igis.mvc.{Controller, Request, Response}
import models._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TreeController extends Controller {
  def titlePath(path: String): Seq[TitlePart] = {
    val parts = path.split("/")
    val urls = parts.foldLeft(Seq[String](""))((prev, cur) => prev ++ Seq(s"${prev.last}/$cur")).drop(1)


    parts.zip(urls).map{case (part, url) => TitlePart(part, s"#/tree$url")}
  }

  def apply(req: Request): Future[Response] = {
    if(!req.remPath.contains("/")) {
      return Future.successful(Response.redirect(s"/repo/${req.remPath}"))
    }

    Tree.files(req.remPath, req.node).map { files =>
      Response.withData(html.tree(files, titlePath(req.remPath), req.remPath).toString())
    }
  }
}