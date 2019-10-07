package pl.javastart.sellegro.auction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    List<Auction> findFirst5ByOrderByPriceDesc();

//    // query for setTitles method from AuctionService
//    @Modifying
//    @Transactional
//    @Query("UPDATE Auction a SET a.title = :title WHERE a.id = :id")
//    void setTitle(long id, String title);
}
