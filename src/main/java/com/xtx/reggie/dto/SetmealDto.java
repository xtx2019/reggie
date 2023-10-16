package com.xtx.reggie.dto;

import com.xtx.reggie.entity.Setmeal;
import com.xtx.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
