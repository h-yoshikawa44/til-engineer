<?php

namespace App\Http\Controllers;

use App\Services\CustomerService;
use Illuminate\Http\Request;

class ApiController extends Controller
{
    public function getCustomers(CustomerService $customer_service)
    {
        return response()->json($customer_service->getCustomers());
    }

    public function postCustomers(Request $request, CustomerService $customer_service)
    {
        $this->validate($request, ['name' => 'required']);
        $customer_service->addCustomer($request->json('name'));
    }

    public function getCustomer()
    {

    }

    public function putCustomer()
    {

    }

    public function deleteCustomer()
    {

    }

    public function getReports()
    {

    }

    public function postReport()
    {

    }

    public function getReport()
    {

    }

    public function putReport()
    {

    }

    public function deleteReport()
    {

    }
}
