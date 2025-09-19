package com.mintospeed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mintospeed.dto.ItemRequest;
import com.mintospeed.dto.ItemResponse;
import com.mintospeed.service.ItemService;

@RestController
@RequestMapping("/products")
public class ItemController {
	@Autowired
	ItemService itemService;
	
	@GetMapping
	public ResponseEntity<?> getAllItems(){
		try {
			
		return ResponseEntity.ok(itemService.getAllItems());
		
		}catch (Exception e){
			
			return ResponseEntity.status(500).body(e);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getItemDetails(@PathVariable("id") Long itemId) {
		try {
			return ResponseEntity.ok(itemService.getItemById(itemId));

		} catch (Exception e) {
			return ResponseEntity.status(404).body("This item does not exist any more.");
		}
	}
	
//	3. Add product (Admin only)
//  @PreAuthorize("hasRole('ADMIN')")

    @PostMapping
    public ResponseEntity<ItemResponse> addProduct(@RequestBody ItemRequest request) {
        return ResponseEntity.ok(itemService.addItem(request));
    }
    
//    4. Edit product (Admin Only)
    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ItemRequest request) {
    	try {
        return ResponseEntity.ok(itemService.updateItem(id, request));
    	}
    	catch(Exception e) {
    		return ResponseEntity.status(404).body(null);
    	}
    }

//    5. Delete product (Admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
 