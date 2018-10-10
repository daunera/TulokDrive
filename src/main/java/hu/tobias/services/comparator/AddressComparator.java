package hu.tobias.services.comparator;

import java.util.Comparator;

import org.apache.commons.lang3.builder.CompareToBuilder;

import hu.tobias.entities.Address;

public class AddressComparator implements Comparator<Address> {

	@Override
	public int compare(Address o1, Address o2) {
		return new CompareToBuilder()
				.append(o1.getCountry(), o2.getCountry())
				.append(o1.getPostcode(), o2.getPostcode())
				.append(o1.getStreet(), o2.getStreet())
				.toComparison();
	}

}
