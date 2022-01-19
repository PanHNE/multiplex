package services

sealed abstract class ServiceError

object Service {
  final case class NotFound(error: String) extends ServiceError
  final case class NotAddNewElement(error: String) extends ServiceError
}