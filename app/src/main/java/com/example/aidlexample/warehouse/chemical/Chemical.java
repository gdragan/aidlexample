package com.example.aidlexample.warehouse.chemical;

import com.example.aidlexample.warehouse.Container;
import com.example.aidlexample.warehouse.ContainerFeature;
import java.io.Serializable;

public abstract class Chemical implements Serializable {
  protected int volume;
  protected ContainerFeature containerFeature;

  public Chemical(int volume, ContainerFeature containerFeature) {
    this.volume = volume;
    this.containerFeature = containerFeature;
  }

  protected Chemical() {
  }

  public double getVolume() {
    return volume;
  }

  public ContainerFeature getContainerFeature() {
    return containerFeature;
  }

  public boolean isSatisfiedBy(Container container) {
    return container.getFeatures().contains(containerFeature);
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }

  public void setContainerFeature(ContainerFeature containerFeature) {
    this.containerFeature = containerFeature;
  }
}
