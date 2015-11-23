package com.github.davidbailey.TransportSim

import org.scalatra._
import scalate.ScalateSupport

class TransportSimServlet extends TransportSimStack {

  get("/") {
    <html>
      <body>
        <h1>Welcome to TransportSim!</h1>
        <a href="/TransportSim/">Get Started</a>.
      </body>
    </html>
  }

  get("/TransportSim/") {
    <html>
      <body>
      </body>
    </html>

  }

}
