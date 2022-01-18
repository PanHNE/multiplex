package bootstrap

import com.google.inject.AbstractModule

class MultiplexDatabaseModule extends AbstractModule{
  override def configure(): Unit = {
    bind(classOf[InitialData]).asEagerSingleton()
  }
}
