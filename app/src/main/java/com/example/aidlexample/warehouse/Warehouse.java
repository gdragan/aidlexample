package com.example.aidlexample.warehouse;

import com.example.aidlexample.warehouse.chemical.Chemical;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {
    private static final Warehouse warehouse = new Warehouse();
    private List<Container> containers = new ArrayList<>();

    public static Warehouse getInstance() {
        return warehouse;
    }

    public Container addToContainer(Chemical chemical) {
        Container container = findContainer(chemical);
        container.add(chemical);
        return container;
    }

    public Container findContainer(Chemical chemical) {
        for (Container container : containers) {
            if (container.canAccommodate(chemical)) {
                return container;
            }
        }
        return createNewContainer(chemical);
    }

    public Container createNewContainer(Chemical chemical) {
        Container container = Container.newInstance(chemical.getContainerFeature());
        containers.add(container);
        return container;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public int getContainersTotalCount() {
        return containers.size();
    }

    public int getContainersCount(ContainerFeature feature) {
        int count = 0;
        for (Container container : containers) {
            List<ContainerFeature> features = container.getFeatures();
            if (features.get(0) == feature) {
                count++;
            }
        }
        return count;
    }
}
