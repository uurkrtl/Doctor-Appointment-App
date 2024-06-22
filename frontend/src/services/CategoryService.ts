import axios from "axios";
import {Category} from "../types/Category.ts";

export default class CategoryService {
    getAllCategories() {
        return axios.get('/api/categories');
    }

    getCategoryById(id: string) {
        return axios.get(`/api/categories/${id}`)
    }

    addCategory(category: Category) {
        return axios.post('/api/categories', category);
    }

    updateCategory(category: Category) {
        return axios.put('/api/categories/', category)
    }

    deleteCategory(id: number) {
        return axios.delete(`/api/categories/delete/${id}`)
    }
}