package org.playground.repo;

//import org.playground.domain.MailBox;
import org.playground.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
