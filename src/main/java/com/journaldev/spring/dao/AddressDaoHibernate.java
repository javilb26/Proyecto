package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Address;
import com.vividsolutions.jts.geom.Point;

@Repository("addressDao")
public class AddressDaoHibernate extends GenericDaoHibernate<Address, Long>
		implements AddressDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Address> getAddresses(Long cityId) {
		return getSession()
				.createQuery(
						"SELECT a FROM Address a WHERE a.city.cityId = :cityId ORDER BY a.name")
				.setParameter("cityId", cityId).list();
	}

	@Override
	public long getNearestAddress(Point position) {
		//TODO Tuve que usar sql directamente si no al pasar el punto fallaba
		Number addressId = (Number) getSession()
				.createSQLQuery(
						"SELECT addressId FROM Address a WHERE ST_Distance(:position, a.location) = (SELECT min(ST_Distance(:position, ad.location)) FROM Address ad)")
				.setParameter("position", position).uniqueResult();
		return addressId.longValue();
	}

}