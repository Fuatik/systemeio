package io.systeme.test_task.repository;

import io.systeme.test_task.model.product.Coupon;

public interface CouponRepository extends BaseRepository<Coupon> {

    Boolean existsByCode (String code);

    Coupon findByCode(String code);
}
