import org.scalatra._

class API extends ScalatraFilter {
  get("/") {
    <h1>Test</h1>
  }
}
