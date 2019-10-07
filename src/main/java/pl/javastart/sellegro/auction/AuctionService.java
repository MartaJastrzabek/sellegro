package pl.javastart.sellegro.auction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.comparator.Comparators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
//        // for setTitles method
//        setTitles(auctionRepository);
        auctionRepository.saveAll(setTitles());

    }

    private static final String[] ADJECTIVES = {"Niesamowity", "Jedyny taki", "IGŁA", "HIT", "Jak nowy",
            "Perełka", "OKAZJA", "Wyjątkowy"};

    //for saveAll method:
    private List<Auction> setTitles() {
        List<Auction> allAuctions = auctionRepository.findAll();
        Random random = new Random();
        String randomAdjective;
        String title;

        for (int i = 0; i < allAuctions.size(); i++) {
            randomAdjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
            title = randomAdjective + " " + allAuctions.get(i).getCarMake() + " " + allAuctions.get(i).getCarModel();
            allAuctions.get(i).setTitle(title);
        }
        return allAuctions;
    }

//    // custom made setTitles method
//    private void setTitles(AuctionRepository auctionRepository) {
//        List<Auction> allAuctions = auctionRepository.findAll();
//        Random random = new Random();
//        String randomAdjective;
//        String title;
//
//        for (Auction auction : allAuctions) {
//            randomAdjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
//            title = randomAdjective + " " + auction.getCarMake() + " " + auction.getCarModel();
//            auctionRepository.setTitle(auction.getId(), title);
//        }
//    }

    public List<Auction> get5mostExpensiveAuctions() {
        return auctionRepository.findFirst5ByOrderByPriceDesc();
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public List<Auction> getAllAuctionsSorted(String sort) {
        return auctionRepository.findAll(Sort.by(sort));
    }

    public List<Auction> findAllForFilters(AuctionFilters auctionFilters) {
        Auction checkFilter = checkAuctionFilters(auctionFilters);
        ExampleMatcher example = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        return auctionRepository.findAll(Example.of(checkFilter, example));
    }

    public List<Auction> findAllForFiltersSorted(AuctionFilters auctionFilters, String sort) {
        Auction checkFilter = checkAuctionFilters(auctionFilters);
        ExampleMatcher example = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        return auctionRepository.findAll(Example.of(checkFilter, example), Sort.by(sort));
    }

    private Auction checkAuctionFilters(AuctionFilters auctionFilters) {
        Auction checkFilter = new Auction();

        if (auctionFilters.getCarMaker() != null && !auctionFilters.getCarMaker().isEmpty()) {
            checkFilter.setCarMake(auctionFilters.getCarMaker());
        }
        if (auctionFilters.getCarModel() != null && !auctionFilters.getCarModel().isEmpty()) {
            checkFilter.setCarModel(auctionFilters.getCarModel());
        }
        if (auctionFilters.getColor() != null && !auctionFilters.getColor().isEmpty()) {
            checkFilter.setColor(auctionFilters.getColor());
        }
        if (auctionFilters.getTitle() != null && !auctionFilters.getTitle().isEmpty()) {
            checkFilter.setTitle(auctionFilters.getTitle());
        }
        return checkFilter;
    }
}
