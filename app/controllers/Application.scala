package controllers

import org.apache.spark.{SparkConf, SparkContext}
import play.api.mvc._
import com.datastax.spark.connector._
import Models.ReducedUnderSpeeding
import scala.collection.mutable.ListBuffer
import org.json4s._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{ write }

//class Application @Inject()(repository: Data.Repository) extends Controller {
class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def demo = Action{
    val sc = new SparkContext(
      new SparkConf()
        .setAppName("Spark Count")
        .setMaster("local[4]")
        .set("spark.executor.memory", "2g")
        .set("spark.cassandra.connection.host", "localhost")
        .set("spark.cassandra.connection.port", "9042")
    )

    var data = sc.cassandraTable("ctrack", "processedunderspeeding")
      .where("latitude > ?", 53.57701542395848)
      .where("latitude < ?", 53.97628192530913)
      .where("longitude > ?", -1.8656158447265625)
      .where("longitude < ?", -1.0842132568359375)
    data.cache

    var results = new ListBuffer[ReducedUnderSpeeding]

//      Leeds BOX
  //    53.97628192530913, -1.8656158447265625 TL
  //    53.98112760380475, -1.1130523681640625  TR
  //    53.57701542395848, -1.0842132568359375  BR
  //    53.607988518088476, -1.8381500244140625 BL
//      Henham
//      52.1166256737882, -0.1517486572265625
//      52.114095768398634, 0.6214141845703125
//      51.60607748386585, 0.6983184814453125
//      51.63847621195153, -0.1682281494140625

    data.collect.foreach(row => {
      val toAdd = new ReducedUnderSpeeding(
        row.getInt("underspeed5below"),
        row.getInt("underspeed5tlt10"),
        row.getInt("underspeed10tlt15"),
        row.getInt("underspeed15tlt20"),
        row.getInt("underspeed20tlt25"),
        row.getInt("underspeed25tlt30"),
        row.getInt("underspeed30tlt35"),
        row.getInt("underspeed35tlt40"),
        row.getInt("underspeed40tlt45"),
        row.getInt("underspeed45tlt50"),
        row.getInt("underspeed50tlt55"),
        row.getInt("underspeed55tlt60"),
        row.getInt("underspeed60tlt65"),
        row.getInt("underspeed65tlt70"),
        row.getInt("underspeed70tlt75"),
        row.getInt("underspeed75tlt80"),
        row.getInt("underspeed80tlt85"),
        row.getInt("underspeed85tlt90"),
        row.getInt("underspeed90tlt95"),
        row.getInt("underspeed95above"),
        row.getDouble("latitude"),
        row.getDouble("longitude")
      )

      results += toAdd
    })

    implicit val formats = org.json4s.DefaultFormats
    var json = write(results)

    Ok(views.html.index(json))
  }
}
