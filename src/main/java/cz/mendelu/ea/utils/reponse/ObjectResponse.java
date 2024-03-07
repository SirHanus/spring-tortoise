package cz.mendelu.ea.utils.reponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ObjectResponse<T> {
    T content;

    // další metainformace


}
