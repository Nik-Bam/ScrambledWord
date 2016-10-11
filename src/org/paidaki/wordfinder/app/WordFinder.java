package org.paidaki.wordfinder.app;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordFinder {

    private HashMap<Character, ArrayList<String>> words = new HashMap<>();

    public boolean loadDictionary(File filePath) {
        HashMap<Character, ArrayList<String>> map = new HashMap<>();

        try (Stream<String> stream = Files.lines(filePath.toPath())) {
            Iterator<String> iter = stream.iterator();

            if (!iter.hasNext()) return false;

            while (iter.hasNext()) {
                String s = iter.next();

                if (!s.matches("\\p{Lu}+")) return false;

                map.putIfAbsent(s.charAt(0), new ArrayList<>());
                map.get(s.charAt(0)).add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        map.forEach((character, list) -> Collections.sort(list));
        words.clear();
        words.putAll(map);

        return true;
    }

    public List<String> searchWord(String word) {
        word = fixWord(word);
        List<Character> wordChars = word.chars().mapToObj(e -> (char) e).sorted().collect(Collectors.toList());
        List<Character> wordUniqueChars = word.chars().distinct().mapToObj(e -> (char) e).collect(Collectors.toList());
        List<String> matches = new ArrayList<>();

        if (words.keySet().containsAll(wordUniqueChars)) {
            for (Character c : wordUniqueChars) {
                for (String s : words.get(c)) {
                    if (s.length() != word.length()) {
                        continue;
                    }
                    List<Character> chars = s.chars().mapToObj(e -> (char) e).sorted().collect(Collectors.toList());

                    if (chars.equals(wordChars)) {
                        matches.add(s);
                    }
                }
            }
        }
        Collections.sort(matches);

        return matches;
    }

    @NotNull
    public String fixWord(String word) {
        return word
                .replaceAll("\\P{L}+", "")
                .replace((char) 0x390, (char) 0x399)
                .replace((char) 0x3B0, (char) 0x399)
                .toUpperCase()
                .replace((char) 0x386, (char) 0x391)
                .replace((char) 0x388, (char) 0x395)
                .replace((char) 0x389, (char) 0x397)
                .replace((char) 0x38A, (char) 0x399)
                .replace((char) 0x38C, (char) 0x39F)
                .replace((char) 0x38E, (char) 0x3A5)
                .replace((char) 0x38F, (char) 0x3A9)
                .replace((char) 0x3AA, (char) 0x399)
                .replace((char) 0x3AB, (char) 0x3A5);
    }
}
