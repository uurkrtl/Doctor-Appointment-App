import React, {useEffect, useState} from 'react';
import {Appointment} from "../types/Appointment.ts";
import {useNavigate} from "react-router-dom";
import AppointmentService from "../services/AppointmentService.ts";
import {Category} from "../types/Category.ts";
import {Complaint} from "../types/Complaint.ts";
import CategoryService from "../services/CategoryService.ts";
import ComplaintService from "../services/ComplaintService.ts";
import CommonFormFields from "../layouts/CommonFormFields.tsx";

function AppointmentDraftAdd() {
    const [appointment, setAppointment] = useState<Appointment>({
        id: '',
        firstName: '',
        lastName: '',
        appointmentDate: new Date(),
        complaintId: '',
        description: '',
        imageUrl: '',
        urgencyScore: 0,
        status: '',
        createdAt: new Date(),
        updatedAt: new Date()
    });

    const navigate = useNavigate();
    const appointmentService = new AppointmentService();
    const categoryService = new CategoryService();
    const complaintService = new ComplaintService();
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [categories, setCategories] = useState<Category[]>([]);
    const [complaints, setComplaints] = useState<Complaint[]>([]);
    const [selectedCategory, setSelectedCategory] = useState<Category | undefined>();

    useEffect(() => {
        categoryService.getAllCategories().then((response) => {
            setCategories(response.data);
        });
    });

    useEffect(() => {
        if (selectedCategory) {
            complaintService.getComplaintByCategoryId(selectedCategory.id).then((response) => {
                setComplaints(response.data);
            });
        }
    }, [complaintService, selectedCategory]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        appointmentService.addAppointmentTask(appointment)
            .then(response => {
                console.log(response)
                navigate(`/appointments/add-details/${response.data.id}`)
            })
            .catch(error => {
                if (error.response) {
                    console.log(error.response.data);
                    setErrorMessage(error.response.data.message);
                }else {
                    console.log('Etwas ist schief gelaufen:', error.message);
                    setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
                }
            });
    }

    return (
        <main className={'container'}>
            <CommonFormFields/>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <div className="row g-3">

                            <div className="col-sm-6">
                                <label htmlFor="firstName" className="form-label">Vorname</label>
                                <input type="text" className="form-control" id="firstName"
                                       placeholder="Schreiben Sie Ihren Vornamen" value={appointment.firstName}
                                       onChange={(e) => setAppointment({...appointment, firstName: e.target.value})}/>
                            </div>

                            <div className="col-sm-6">
                                <label htmlFor="lastName" className="form-label">Nachname</label>
                                <input type="text" className="form-control" id="lastName"
                                       placeholder="Schreiben Sie Ihren Nachnamen" value={appointment.lastName}
                                       onChange={(e) => setAppointment({...appointment, lastName: e.target.value})}/>
                            </div>

                        </div>

                        <div className="row g-3 mt-3">

                            <div className="col-sm-6">
                                <label htmlFor="categoryName" className="form-label">Kategoriename</label>
                                <select className="form-select"
                                        id="categoryName"

                                        onChange={(e) => setSelectedCategory(categories.find(category => category.id === e.target.value))}
                                >
                                    <option value="">Wählen Sie eine Kategorie</option>
                                    {categories.map((category) => {
                                        return (
                                            <option key={category.id} value={category.id}>
                                                {category.name}
                                            </option>
                                        )
                                    })};
                                </select>
                            </div>

                            <div className="col-sm-6">
                                <label htmlFor="complaintName" className="form-label">Beschwerde</label>
                                <select className="form-select"
                                        id="complaintName"
                                        onChange={(e) => setAppointment({...appointment, complaintId: e.target.value})}
                                >
                                    <option value="">Wählen Sie eine Kategorie</option>
                                    {complaints.map((complaint) => {
                                        return (
                                            <option key={complaint.id}
                                                    value={complaint.id}>{complaint.name}</option>
                                        )
                                    })};
                                </select>
                            </div>
                        </div>

                            <button className="w-100 btn btn-primary btn-lg my-4" type="submit">Weiter</button>
                    </form>

                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}

                </div>
            </div>
        </main>
    );
}

export default AppointmentDraftAdd;