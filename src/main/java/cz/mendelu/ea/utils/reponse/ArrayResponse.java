package cz.mendelu.ea.utils.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArrayResponse<T> {
    List<T> items;

    // meta

    int count;
}
