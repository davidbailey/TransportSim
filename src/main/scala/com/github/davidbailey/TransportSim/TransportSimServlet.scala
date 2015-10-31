package com.github.davidbailey.TransportSim

import org.scalatra._
import scalate.ScalateSupport

class TransportSimServlet extends TransportSimStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

}
