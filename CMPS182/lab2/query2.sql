SELECT h.name, (h.price-n.price) AS "Price Difference" FROM h_medicines h, new_medicines n WHERE h.name = n.name ORDER BY (h.price-n.price) DESC;
