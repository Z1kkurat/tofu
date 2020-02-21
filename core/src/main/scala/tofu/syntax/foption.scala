package tofu.syntax

import cats.Monad
import cats.syntax.applicative._
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.option._
import tofu.Raise

object foption {
  implicit final class FOptionOps[F[_], A](private val lhs: F[Option[A]]) extends AnyVal {
    def getOrElseF(fa: => F[A])(implicit F: Monad[F]): F[A] =
      lhs.flatMap(_.fold(fa)(_.pure[F]))
    def orElseF(fa: => F[Option[A]])(implicit F: Monad[F]): F[Option[A]] =
      lhs.flatMap(_.fold(fa)(_.some.pure[F]))
    def orThrow[E](err: => E)(implicit F: Monad[F], FE: Raise[F, E]): F[A] =
      lhs.flatMap(_.fold(FE.raise[A](err))(_.pure[F]))
    def flatMapOpt[B](f: A => F[B])(implicit F: Monad[F]): F[Option[B]] =
      lhs.flatMap(_.fold(none[B].pure[F])(f(_).map(_.some)))
    def doubleFlatMap[B](f: A => F[Option[B]])(implicit F: Monad[F]): F[Option[B]] =
      lhs.flatMap(_.fold(none[B].pure[F])(f(_)))
  }
}
