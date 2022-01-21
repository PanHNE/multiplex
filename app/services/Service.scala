package services

sealed abstract class ServiceError

object Service {
  final case class NotFound(message: String) extends ServiceError
  final case class NotAddNewElement(message: String) extends ServiceError
}