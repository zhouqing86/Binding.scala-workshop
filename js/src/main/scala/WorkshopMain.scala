import scala.scalajs.js.annotation.JSExport

import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.dom

import com.thoughtworks.binding.Binding._

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.Binding.Vars
import com.thoughtworks.binding.Binding.BindingSeq
import com.thoughtworks.binding.Binding.FutureBinding

import org.scalajs.dom.raw.Event
import org.scalajs.dom.raw.Node
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.html.Table
import org.scalajs.dom.html.Input

import org.scalajs.dom.ext.Ajax
import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.util._

@JSExport
object WorkshopMain {

  def int: Int = 1

  @dom
  def foo: Binding[Int] = bar.each * 2

  val bar: Var[Int] = Var(2)

  @dom
  def baz: Binding[Int] = {
    foo.each + bar.each + int
  }

  @dom
  def page = {
    val githubUserName = Var("")

    <input className="my-input" type="text" onchange={ event: Event =>
      githubUserName := event.currentTarget.asInstanceOf[Input].value
    }/>
    <div>
      {
        githubUserName.each match {
          case ""=> <div>Please input your Github user name</div>
          case nameValue =>

            import org.scalajs.dom.XMLHttpRequest
            import scala.concurrent.Future
            val future: Future[XMLHttpRequest] = Ajax.get(s"https://api.github.com/users/${nameValue}")
            val githubResult: Binding[Option[Try[XMLHttpRequest]]] = FutureBinding(future)
            githubResult.each match {
              case None =>
                <div>Loading</div>
              case Some(Success(xmlHttpRequest)) =>
                import scala.scalajs.js.JSON
                val json = JSON.parse(xmlHttpRequest.responseText)
                <img src={ json.avatar_url.toString }/>
              case Some(Failure(exception)) =>
                <div>{ exception.toString }</div>
            }
        }
      }
    </div>
    <hr/>
    <div>{ baz.each.toString }</div>
    <button onclick={ event: Event =>
      bar := 0
    }></button>

  }

  @JSExport
  def main(): Unit = {
    import org.scalajs.dom.document
    dom.render(document.body, page)
  }

}
