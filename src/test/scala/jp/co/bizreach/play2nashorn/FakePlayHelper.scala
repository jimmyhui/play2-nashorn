package jp.co.bizreach.play2nashorn

import java.io.File

import com.typesafe.config.{ConfigFactory, ConfigRenderOptions}
import play.api.Configuration
import play.api.test.FakeApplication
import play.api.test.Helpers._

import scala.collection.JavaConverters._

trait FakePlayHelper {

  def PlayApp(configs:(String, Any)*) = {
//    val configFromFile = ConfigFactory.parseFile(
//      new File(this.getClass.getResource("/conf/application.conf").toURI))
//      .entrySet.asScala.map(entry => entry.getKey -> entry.getValue.render(ConfigRenderOptions.concise())).toMap

    FakeApplication(
      path = new File(this.getClass.getResource("/").toURI),
//      additionalPlugins = Seq(
//        "jp.co.bizreach.play2nashorn.NashornPlugin"
//      ),
      additionalConfiguration = //configFromFile ++
        configs.toSet.toMap
        //+ ("play2stub.view-root" -> "/views")
        //+ ("play2stub.data-root" -> "/data")
    )}

  def runApp[T](app: FakeApplication)(block: FakeApplication => T): T = {
    running(app) {
      block(app)
    }
  }

  def parseConfigFile(path: String):Map[String, String] = {
    new Configuration(ConfigFactory.load(path))
      .entrySet.map(entry => entry._1 -> entry._2.render()).toMap
  }
}
