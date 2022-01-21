import com.google.inject.AbstractModule
import daos._
import services._

class Module extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[FilmDAO]).to(classOf[FilmDAOImpl])
    bind(classOf[FilmService]).to(classOf[FilmServiceImpl])
    bind(classOf[RoomDAO]).to(classOf[RoomDAOImpl])
    bind(classOf[RoomService]).to(classOf[RoomServiceImpl])
    bind(classOf[ScreeningDAO]).to(classOf[ScreeningDAOImpl])
    bind(classOf[ScreeningService]).to(classOf[ScreeningServiceImpl])
    bind(classOf[SeatDAO]).to(classOf[SeatDAOImpl])
    bind(classOf[SeatService]).to(classOf[SeatServiceImpl])
  }
}
