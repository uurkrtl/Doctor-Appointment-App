package de.schnellertermin.backend.repositories;

import de.schnellertermin.backend.models.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
}
