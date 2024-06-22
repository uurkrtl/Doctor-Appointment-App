import axios from "axios";
import {Appointment} from "../types/Appointment.ts";

export default class AppointmentService {
    getAllAppointments() {
        return axios.get('/api/appointments')
    }

    getAppointmentById(id: string) {
        return axios.get(`/api/appointments/${id}`)
    }

    addAppointmentTask(appointment: Appointment) {
        return axios.post('api/appointments', appointment)
    }

    updateAppointmentScore(id: string, imageUrl: string, description: string) {
        return axios.put(`/api/appointments/set-score/${id}?imageUrl=${imageUrl}&description=${description}`)
    }

    updateAppointmentStatus(id: string, status: string) {
        return axios.put(`/api/appointments/status/${id}`, {status})
    }
}