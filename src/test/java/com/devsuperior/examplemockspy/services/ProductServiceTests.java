package com.devsuperior.examplemockspy.services;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.examplemockspy.dto.ProductDTO;
import com.devsuperior.examplemockspy.entities.Product;
import com.devsuperior.examplemockspy.repositories.ProductRepository;
import com.devsuperior.examplemockspy.services.exceptions.InvalidDataException;
import com.devsuperior.examplemockspy.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;

	@Mock
	private ProductRepository repository;

	private Long existingId, nonExistingId;
	private Product product;
	private ProductDTO productDTO;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;

		product = new Product(1L, "Psp", 1000.0);
		productDTO = new ProductDTO(product);

		Mockito.when(repository.save(any())).thenReturn(product);

		Mockito.when(repository.getReferenceById(existingId)).thenReturn(product);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
	}

	@Test
	public void insertShouldReturnProductDTOWhenValidDate() {
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doNothing().when(serviceSpy).validateData(productDTO);

		ProductDTO result = serviceSpy.insert(productDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getName(), "Psp");
	}

	@Test
	public void insertShouldReturnInvalidDataExceptionWhenProductNameIsBlank() {
		productDTO.setName("");

		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);

		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.insert(productDTO);
		});
	}

	@Test
	public void insertShouldReturnInvalidDataExceptionWhenProductPriceIsNegativeOrZero() {
		productDTO.setPrice(-1.0);

		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);

		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.insert(productDTO);
		});
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExistsAndValidDate() {
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doNothing().when(serviceSpy).validateData(productDTO);

		ProductDTO result = serviceSpy.update(existingId, productDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}

	@Test
	public void updateShouldReturnInvalidDataExceptionWhenProductNameIsBlankAndIdExists() {
		productDTO.setName("");

		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);

		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(existingId, productDTO);
		});
	}
	
	@Test
	public void updateShouldReturnInvalidDataExceptionWhenProductPriceIsNegativeOrZeroAndIdExists() {
		productDTO.setPrice(-1.0);

		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);

		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(existingId, productDTO);
		});
	}
	
	@Test 
	public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExistsAndValidDate() {
		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doNothing().when(serviceSpy).validateData(productDTO);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(nonExistingId, productDTO);
		});
	}
	
	@Test
	public void updateShouldReturnInvalidDataExceptionWhenProductNameIsBlankAndIdDoesNotExists() {
		productDTO.setName("");

		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);

		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(nonExistingId, productDTO);
		});
	}
	
	@Test
	public void updateShouldReturnInvalidDataExceptionWhenProductPriceIsNegativeOrZeroAndIdDoesNotExists() {
		productDTO.setPrice(-1.0);

		ProductService serviceSpy = Mockito.spy(service);
		Mockito.doThrow(InvalidDataException.class).when(serviceSpy).validateData(productDTO);

		Assertions.assertThrows(InvalidDataException.class, () -> {
			@SuppressWarnings("unused")
			ProductDTO result = serviceSpy.update(nonExistingId, productDTO);
		});
	}

}
