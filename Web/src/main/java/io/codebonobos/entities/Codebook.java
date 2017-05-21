package io.codebonobos.entities;

import io.codebonobos.utils.CodebookEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by afilakovic on 21.05.17..
 */
public class Codebook {
    private List<CodebookEntry> entries;

    public Codebook() {
        this.entries = new ArrayList<>();
    }

    public void add(CodebookEntry e){
        this.entries.add(e);
    }

    public List<CodebookEntry> getEntries() {
        return entries;
    }
}
