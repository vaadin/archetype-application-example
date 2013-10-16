package org.vaadin.mockapp.ui.views.admin;

import com.vaadin.data.Validator;
import org.vaadin.mockapp.Services;
import org.vaadin.mockapp.backend.domain.Doctor;
import org.vaadin.mockapp.backend.services.DoctorService;

import java.util.UUID;

/**
 * @author petter@vaadin.com
 */
public class UniqueDoctorCodeValidator implements Validator {

    private UUID existingUuid;

    public void setExistingUuid(UUID existingUuid) {
        this.existingUuid = existingUuid;
    }

    @Override
    public void validate(Object value) throws InvalidValueException {
        Doctor found = Services.get(DoctorService.class).findByCode((String) value);
        if (found != null && !found.getUuid().equals(existingUuid)) {
            throw new InvalidValueException("A doctor with this code already exists");
        }
    }
}
