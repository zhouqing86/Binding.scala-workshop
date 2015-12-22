import scala.scalajs.js.annotation.JSExport

import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.dom

import com.thoughtworks.binding.Binding._

import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.Binding.Vars
import com.thoughtworks.binding.Binding.BindingSeq

import org.scalajs.dom.raw.Event
import org.scalajs.dom.raw.Node
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.html.Table
import org.scalajs.dom.html.Input

@JSExport
object WorkshopMain {

  @dom
  def page = {
    <p>Hello, World!</p>
  }

  @JSExport
  def main(): Unit = {
    import org.scalajs.dom.document
    dom.render(document.body, page)
  }

}
