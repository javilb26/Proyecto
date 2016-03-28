package com.journaldev.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.journaldev.spring.dao.util.GenericDaoHibernate;
import com.journaldev.spring.model.Address;

@Repository("AddressDao")
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

}