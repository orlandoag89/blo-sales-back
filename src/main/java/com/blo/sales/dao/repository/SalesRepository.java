package com.blo.sales.dao.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.blo.sales.dao.docs.ProductsOnSaleCounter;
import com.blo.sales.dao.docs.Sale;
import com.blo.sales.dao.docs.SaleDetailReport;

@Repository
public interface SalesRepository extends MongoRepository<Sale, String> {
	
	@Query("{ 'close_sale' : 0 }")
	List<Sale> findSalesOpen();

	@Query("{ 'close_sale' : { $ne: 0 } }")
	List<Sale> findSalesClosed();
	
	@Query(" { 'is_on_cashbox': false } ")
	List<Sale> findSaleNoCashbox();
	
	@Aggregation(pipeline = {
        "{ $unwind: '$products' }",
        "{ $group: { " +
            "_id: '$products.name', " +
            "total_sold: { $sum: { $toDouble: '$products.quantity_on_sale' } }, " +
            "total_revenue: { $sum: { $toDouble: '$products.total_price' } }, " +
            "time_sold: { $sum: 1 } " +
        "} }",
        "{ $sort: { total_sold: -1 } }"
    })
	List<ProductsOnSaleCounter> countSalesByProduct();
	
	@Aggregation(pipeline = {
	    "{ $match: { $and: ?#{#matchConditions} } }",
	    "{ $unwind: '$products' }",
	    "{ $group: { " +
	        "_id: '$products.name', " +
	        "total_sold: { $sum: { $toDouble: '$products.quantity_on_sale' } }, " +
	        "total_revenue: { $sum: { $toDouble: '$products.total_price' } }, " +
	        "time_sold: { $sum: 1 } " +
	    "} }",
	    "{ $sort: { total_sold: -1 } }"
	})
	List<ProductsOnSaleCounter> countSalesByProduct(List<Document> matchConditions);

	@Aggregation(pipeline = {
		"{ $match: ?0 }",
	    "{ $group: { _id: { year: { $year: { $toDate: \"$open_date\" } }, month: { $month: { $toDate: \"$open_date\" } } }, totalVentas: { $sum: { $toDouble: \"$total\" } } } }",
	    "{ $project: { year: '$_id.year', month: '$_id.month', totalVentas: 1, _id: 0 } }",
	    "{ $sort: { year: 1, month: 1 } }"
	})
	List<SaleDetailReport> retrieveSalesByDate(Document matchConditions);
}
