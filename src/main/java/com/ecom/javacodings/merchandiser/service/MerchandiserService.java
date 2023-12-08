package com.ecom.javacodings.merchandiser.service;

import com.ecom.javacodings.common.page.PageConstructor;
import com.ecom.javacodings.common.transfer.ItemDTO;
import com.ecom.javacodings.common.page.PageDTO;
import com.ecom.javacodings.common.transfer.ItemImageDTO;
import org.springframework.beans.factory.annotation.Value;
import com.ecom.javacodings.common.transfer.OrderDTO;
import com.ecom.javacodings.merchandiser.access.ItemManagerDAO;
import com.ecom.javacodings.merchandiser.access.OrderManagerDAO;
import com.ecom.javacodings.merchandiser.access.TagManagerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service("mdService")
public class MerchandiserService implements ManagerService {
    // Region Data access objects

    @Value("${spring.servlet.multipart.location}")
    String filePath;

    @Autowired ItemManagerDAO itemDAO;
    @Autowired TagManagerDAO  tagDAO;
    @Autowired OrderManagerDAO  orderDAO;
    // End Region Data access objects
    // Region 상품 관리

    final int DEFAULT_PRODUCT_ROW = 30;
    PageConstructor productPageConstructor = new PageConstructor(DEFAULT_PRODUCT_ROW,
            (String criteria, PageDTO pageData) -> Collections.singletonList(itemDAO.findAll(pageData)),
            (String criteria) -> itemDAO.count()
    );

    // * 기본 CRUD Methods -----------------------------------------------------
    @Override public void setProductPageRow(int row) { productPageConstructor.setRow(row); }

    @Override
    public Map<String, Object> getProductPageMap(int currentPage) {
        Map<String, Object> resultMap = productPageConstructor.getPageMapOrNull(currentPage);
        if (resultMap == null) resultMap.put("responseMsg", "outboundError");
        return resultMap;
    }


    // 기본 CRUD 메소드 =================================
    @Override public ItemDTO readItemById(String id) { return itemDAO.getItemById(id); }
    @Override public int updateItem(ItemDTO item) { return itemDAO.updateItem(item); }
    @Override public int deleteItem(ItemDTO item) { return itemDAO.deleteItem(item); }
    @Override public int deleteItemImages(ItemDTO item) { return itemDAO.deleteItem(item); }
    @Override public int deleteItemTegs(ItemDTO item) { return itemDAO.deleteItem(item); }

    @Override
    public String createItem(ItemDTO item) {
        Random random = new Random();
        int leftLimit  =  48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        String randomItemID;
        ItemDTO checkDuplicate = new ItemDTO();
        do {
            randomItemID = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            checkDuplicate = itemDAO.getItemById(randomItemID);
        } while ( checkDuplicate != null );
        item.setItem_id(randomItemID);

        int result = itemDAO.createItem(item);
        if (result > 0) return randomItemID;
        else return "error";
    }

    @Override
    public int updateImage(String itemId, MultipartFile file) {
        String absoluteClassPath = new File("").getAbsolutePath() + "/src/main/webapp/";
        File targetFile;

        // 이미지 파일 이름을 20자리 랜덤 문자열로 지정
        Random random = new Random();
        int leftLimit  =  48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        String randomImageName;
        do {
            randomImageName = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            targetFile = new File(absoluteClassPath + filePath + "/" + randomImageName + ".png");
        } while ( targetFile.exists() );

        int result = 0;
        try {
            targetFile.getParentFile().mkdirs();
            file.transferTo(targetFile);
            result = itemDAO.updateImageById(itemId, randomImageName + ".png");
        }
        catch (IOException e) { return -1; };
        return result;
    }

    @Override
    public List<ItemDTO> listItem(PageDTO page){
        List<ItemDTO> result = itemDAO.findAll(page);
        return result;
    }
    @Override
    public int updateTags(String item_id, List<String> tags) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("item_id", item_id);
        params.put("tags", tags);

        int result = 0;
        result += tagDAO.deleteTagsByItemId(item_id);
        if (!tags.isEmpty()) result *= tagDAO.insertTags(params);
        return result;
    }


    // Region 상품 메타 정보
    public List<String> listCategory() {
        List<String> result = itemDAO.listCategory();
        return result;
    }

    public List<String> findAllTags() {
        return tagDAO.findAll();
    }

//    @Override
//    public List<TagDTO> listTagsById(String itemId) {
//        return tagDAO.listTagsById(itemId);
//    }
    public int countItems() { return itemDAO.count(); }
    // End Region 상품 메타 정보
    // Region Order
    // READ ===============================
    @Override
    public List<OrderDTO> listOrder(PageDTO page) {
        return orderDAO.orderList(page);
    }
    public OrderDTO orderUpdate(OrderDTO order) {
        return orderDAO.orderUpdate(order); // 주문 상태 변경
    }
    
    @Override
    public List<OrderDTO> orderList(PageDTO page) {
    	return orderDAO.orderList(page);
    }
    @Override
    public int countOrders() { return orderDAO.countOrders(); }
    @Override
    public List<OrderDTO> countOrderState() {
        List<OrderDTO> result = orderDAO.countState();
        String[] states = {"장바구니", "결제 완료", "주문 확인", "배송 시작", "배송 중", "배송 완료", "환불", "반품", "처리 완료"};

        for (OrderDTO order : result) {
            order.setOrder_id(states[order.getState()]);
        }
        return result;
    }
    public int orderStateCnt(OrderDTO order) {
    	return orderDAO.orderStateCnt(order);
    }

    @Override
    public List<ItemImageDTO> findImagesByItemId(String itemId) {
        return itemDAO.findImagesByItemId(itemId);
    }

    @Override
    public String[] findTagsByItemId(String itemId) {
        return tagDAO.findAllByItemId(itemId);
    }

    // EDIT ===============================
    @Override
    public int updateOrderStates(OrderDTO orders) {
        return orderDAO.updateOrderStates(orders); // 주문 상태 변경
    }
    // End Region Order
}
