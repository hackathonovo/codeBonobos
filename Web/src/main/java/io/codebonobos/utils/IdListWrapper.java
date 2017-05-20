package io.codebonobos.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by afilakovic on 20.05.17..
 */
public class IdListWrapper {
    private IdWrapper[] data;

    public IdListWrapper() {
    }

    public IdListWrapper(IdWrapper[] data) {
        this.data = data;
    }

    public List<IdWrapper> getData() {
        return Arrays.asList(data);
    }

    public void setData(IdWrapper[] data) {
        this.data = data;
    }
}
