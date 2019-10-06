package pl.javastart.sellegro.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.javastart.sellegro.auction.Auction;
import pl.javastart.sellegro.auction.AuctionRepository;
import pl.javastart.sellegro.auction.AuctionService;

import java.util.List;

@Controller
public class HomeController {

    private AuctionService auctionService;

    public HomeController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Auction> fiveMostExpensiveCars = auctionService.get5mostExpensiveAuctions();
        model.addAttribute("cars", fiveMostExpensiveCars);
        return "home";
    }
}
