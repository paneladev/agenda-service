package pdev.com.agenda.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pdev.com.agenda.client.BeerClient;
import pdev.com.agenda.client.response.BeerResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BeerService {

    private final BeerClient beerClient;

    public List<BeerResponse> getRandomBeer() {
        return beerClient.getRandomBeer();
    }

    public List<BeerResponse> getBeerById(Long id) {
        return beerClient.getBeerById(id);
    }
}
