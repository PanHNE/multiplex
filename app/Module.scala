import com.google.inject.AbstractModule
import daos.{FilmDAO, FilmDAOImpl}
import services.{FilmService, FilmServiceImpl}

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[FilmDAO]).to(classOf[FilmDAOImpl])
    bind(classOf[FilmService]).to(classOf[FilmServiceImpl])
  }
}
