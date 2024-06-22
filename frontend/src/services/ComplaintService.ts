import axios from "axios";
import {Complaint} from "../types/Complaint.ts";

export default class ComplaintService {
    getAllComplaints() {
        return axios.get('/api/complaints')
    }

    getComplaintByCategoryId(categoryId: string) {
        return axios.get(`/api/complaints/${categoryId}`)
    }

    addComplaint(complaint: Complaint) {
        return axios.post('api/complaints', complaint)
    }
}