package it.uniroma2.dicii.bd.model.domain;

import java.util.ArrayList;
import java.util.List;

public class AdList {
    List<Ad> ads = new ArrayList<>();

    public int getSize(){
        return ads.size();
    }
    public List<Ad> getAds() {
        return ads;
    }
    public void addAd(Ad AD) {
        this.ads.add(AD);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Ad ad : ads) {
            sb.append(ad);
        }
        return sb.toString();
    }

}
