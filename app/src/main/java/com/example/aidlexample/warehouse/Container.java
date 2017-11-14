package com.example.aidlexample.warehouse;

import com.example.aidlexample.warehouse.chemical.Chemical;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Container implements Serializable {
  private static double VOLUME = 1000;
  private final List<ContainerFeature> features = new ArrayList<>();
  private List<Chemical> contents = new ArrayList<>();

  public static Container newInstance(ContainerFeature containerFeature) {
    Container container = new Container();
    container.addFeature(containerFeature);
    return container;
  }

  public boolean hasSpaceFor(Chemical chemical) {
    return remainingSpace() >= chemical.getVolume();
  }

  public double remainingSpace() {
    double totalContentSize = 0.0;
    Iterator it = contents.iterator();
    while (it.hasNext()) {
      Chemical chemical = (Chemical) it.next();
      totalContentSize = totalContentSize + chemical.getVolume();
    }
    return VOLUME - totalContentSize;
  }

  public boolean canAccommodate(Chemical chemical) {
    return hasSpaceFor(chemical) &&
        chemical.isSatisfiedBy(this);
  }

  public boolean isSafelyPacked() {
    Iterator it = contents.iterator();
    while (it.hasNext()) {
      Chemical chemical = (Chemical) it.next();
      if (!chemical.isSatisfiedBy(this)) {
        return false;
      }
    }
    return true;
  }

  public void addFeature(ContainerFeature feature) {
    features.add(feature);
  }

  public List<ContainerFeature> getFeatures() {
    return features;
  }

  public void add(Chemical chemical) {
    contents.add(chemical);
  }
}