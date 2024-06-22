import axios from "axios";
import {Complaint} from "../types/Complaint.ts";

export default class ComplaintService {
    getAllComplaints() {
        return axios.get('/api/complaints')
    }

    addComplaint(complaint: Complaint) {
        return axios.post('api/complaints', complaint)
    }
}