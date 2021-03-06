package tofu.internal

trait DataEffectComp[TC[_[_], _]] {
  @inline final def apply[F[_], A](implicit instance: TC[F, A]): TC[F, A] = instance
}

trait EffectComp[TC[_[_]]] {
  @inline final def apply[F[_]](implicit instance: TC[F]): TC[F] = instance
}

trait DataComp[TC[_]] {
  @inline final def apply[A](implicit instance: TC[A]): TC[A] = instance
}
