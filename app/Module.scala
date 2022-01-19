import com.google.inject.AbstractModule
import daos.{FilmDAO, FilmDAOImpl, RoomDAO, RoomDAOImpl, ScreeningDAO, ScreeningDAOImpl}
import services.{FilmService, FilmServiceImpl, RoomService, RoomServiceImpl, ScreeningService, ScreeningServiceImpl}

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[FilmDAO]).to(classOf[FilmDAOImpl])
    bind(classOf[FilmService]).to(classOf[FilmServiceImpl])
    bind(classOf[RoomDAO]).to(classOf[RoomDAOImpl])
    bind(classOf[RoomService]).to(classOf[RoomServiceImpl])
    bind(classOf[ScreeningDAO]).to(classOf[ScreeningDAOImpl])
    bind(classOf[ScreeningService]).to(classOf[ScreeningServiceImpl])
  }
}
