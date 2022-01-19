import com.google.inject.AbstractModule
import daos.{FilmDAO, FilmDAOImpl, RoomDAO, RoomDAOImpl}
import services.{FilmService, FilmServiceImpl, RoomService, RoomServiceImpl}

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[FilmDAO]).to(classOf[FilmDAOImpl])
    bind(classOf[FilmService]).to(classOf[FilmServiceImpl])
    bind(classOf[RoomDAO]).to(classOf[RoomDAOImpl])
    bind(classOf[RoomService]).to(classOf[RoomServiceImpl])
  }
}
