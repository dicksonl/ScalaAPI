package Data

import com.datastax.driver.core.{Cluster, PoolingOptions}
import Models.ReducedUnderSpeeding
import scala.collection.mutable.ListBuffer

object CassandraConnector {
  val pool = new PoolingOptions()
  val cluster = Cluster.builder()
    .addContactPoint("localhost")
    .withPoolingOptions(pool)
    .build();
}

class Repository {

  def getUnderSpeeding(): ListBuffer[ReducedUnderSpeeding] ={
    var list = new ListBuffer[ReducedUnderSpeeding]
    val session = CassandraConnector.cluster.connect("ctrack")

    var results = session.executeAsync("SELECT * FROM processedunderspeeding")
    var rows = results


    session.close()
    return list
  }
}
