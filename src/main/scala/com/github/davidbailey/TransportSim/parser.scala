object Parser { // https://tuxdna.wordpress.com/2014/02/03/a-simple-scala-parser-to-parse-44gb-wikipedia-xml-dump/
  import Models._
  import scala.io.Source
  import scala.xml.XML
  import scala.xml.pull._
  import scala.collection.mutable.ListBuffer

  def Parse: (List[Models.Node],List[Models.Way]) = {
    val mapFileName = System.getProperty("user.home") + "/TransportSim/var/los-angeles_california.osm"
    val xml = new XMLEventReader(Source.fromFile(mapFileName))
    val mutableNodes = new ListBuffer[Models.Node]
    val mutableWays = new ListBuffer[Models.Way]
    var wayNodes = new ListBuffer[Models.Node]
    var id = ""

    val nodeMatch = """.*lon="(.*)" lat="(.*)" id="(.*)".*""".r
    val wayMatch = """.*id="(.*)".*""".r

    for (event <- xml) {
      event match {
	case EvElemStart(_, "node", tags, _) => {
	  val nodeMatch(lon,lat,id) = tags.toString
	  val node = new Models.Node(BigInt(id),Models.Point(BigDecimal(lat),BigDecimal(lon)))
	  mutableNodes += node
	}
	case EvElemEnd(_, "node") => {
	}
	case EvElemStart(_, "way", tags, _) => {
	  val wayMatch(tagId) = tags.toString
	  id = tagId
	}
	case EvElemEnd(_, "way") => {
	  val way = new Models.Way(BigInt(id),wayNodes.toList)
	  mutableWays += way
	  wayNodes.clear
	}
	case EvElemStart(_, "relation", tags, _) => {
	}
	case EvElemEnd(_, "relation") => {
	}
	case EvElemStart(_, "tag", tags, _) => {
	}
	case EvElemEnd(_, "tag") => {
	}
	case EvElemStart(_, "nd", tags, _) => {
	  //val node = find the node where tags.ref == id
	  //wayNodes += node
	}
	case EvElemEnd(_, "nd") => {
	}
	case _ =>
      }
    }

    val Nodes = mutableNodes.toList
    val Ways = mutableWays.toList
    return (Nodes,Ways)
  }
}
