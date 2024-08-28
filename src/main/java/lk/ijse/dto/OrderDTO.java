package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {
    String orderId;
    Date orderDate;
    String cusIdOption;
    String itemIdOption;
    int orderQty;
    double total;
    double txtCash;
    double txtDiscount;
    private List<ItemDTO> items;
}
