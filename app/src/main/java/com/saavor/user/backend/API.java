package com.saavor.user.backend;

/**
 * Created by user on 4/7/2017.
 */
public class API {

    public static String fsignup() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/SignUp";

    }

    public static String insertfavdish() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UserFavoriteDishInsert";

    }

    public static String reorder() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/ReOrderNew";
    }


    public static String addcard() {
        return "http://demosaavorapi.saavor.io/API/StripePaymentGateway.svc/AddCard";

    }

    public static String listcard() {
        return " http://demosaavorapi.saavor.io/API/StripePaymentGateway.svc/GetCardList/";

    }

    public static String GetOrderStatus() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetOrderStatus/";

    }

    public static String carddetails() {
        return " http://demosaavorapi.saavor.io/API/StripePaymentGateway.svc/CardDetail/";

    }


    public static String deletecard() {
        return "http://demosaavorapi.saavor.io/API/StripePaymentGateway.svc/DeleteCard";

    }

    public static String deletefavdish() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UserFavoriteDishDelete";

    }

    public static String submitenquiry() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/OrderEnquiryInsert";

    }

    public static String submitreviews() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/OrderReviewInsertUpdate";

    }

    public static String getreviews() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetKitchenReviews/";

    }


    public static String getkitchenreviews() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetKitchenReviews/";

    }

    public static String todaysmenu() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetKitchenTodaysMenu";

    }

    public static String orderhistorydetails() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetOrderDetailHistory";

    }

    public static String customizeapi() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetKitchenTodayDish";

    }

    public static String GetOrderHistory() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetOrderHistory";

    }

    public static String emptycart() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/EmptyCart";

    }

    public static String cartpromo() {
        return " http://demosaavorapi.saavor.io/API/UsersService.svc/PromoCodeValidate";

    }

    public static String addtocart() {
        return " http://demosaavorapi.saavor.io/API/UsersService.svc/AddToCart";

    }

    public static String cardetailshit() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetCartDetail/";

    }

    public static String loginApi() {
        return " http://demosaavorapi.saavor.io/API/UsersService.svc/UserLogin";
    }

    public static String addresslist() {
        return "http://demosaavorapi.saavor.io/api/UsersService.svc/GetUserAddressBook/";
    }

    public static String deleteAddress() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/AddressDelete/";
    }

    public static String changepassword() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/ChangePassword";
    }

    public static String viewbookmark() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetBookmarks";
    }

    public static String deletebookmark() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UserBookmarkDelete/";
    }

    public static String addbookmark() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UserBookmarkInsert";
    }

    public static String pupdate() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UserProfileUpdate";
    }

    public static String feedbackapi() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/FeedbackInsertUpdate";
    }

    public static String addaddressapi() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UserAddressesInsertUpdate";
    }

    public static String forgot() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/ForgotPassword/";
    }

    public static String favouriteapi() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetFavoriteDishes";
    }

    public static String userrefrerral() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UserReferral";
    }

    public static String userinfo() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetUserInfo/";
    }

    public static String dashboarddeals() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetDashboardDeals";
    }

    public static String test() {

        return "http://demosaavorapi.saavor.io/API/KitchenService.svc/SendNotifications";
    }

    public static String KitchenSearch() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/KitchenSearch";
    }

    public static String validateemail() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/ValidateEmail/";
    }

    public static String bestoffer() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetDashboardBestOffers";
    }

    public static String cusineapi() {

        return "http://demosaavorapi.saavor.io/API/KitchenService.svc/GetCuisineAndMenuList";
    }

    public static String KitchenInfo() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetKitchenInfo";
    }

    public static String placeorder() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/PlaceOrder";
    }

    public static String cancelorder() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/OrderCancel";
    }

    public static String notificationlist() {

        return " http://demosaavorapi.saavor.io/API/UsersService.svc/GetNotificationList/";
    }

    public static String fGetChefWithRating() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetChefWithRating";
    }

    public static String fGetChefWithDeals() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetChefWithDeals";
    }

    public static String fChefSearch() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/ChefSearch";
    }

    public static String fGetChefProfile() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetChefProfile";
    }

    public static String fGetChefMenus() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetChefMenus";
    }

    public static String fGetCompanyProfile() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetCompanyProfile";
    }

    public static String fPromoCodeValidate() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/PromoCodeValidate";
    }

    public static String fBookingInsert() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/BookingInsert";
    }

    public static String notificationreadhit() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/NotificationRead";
    }

    public static String fUserBookmarkInsert() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UserBookmarkInsert";
    }

    public static String fUserBookmarkDeleteNew() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UserBookmarkDeleteNew";
    }

    public static String fGetBookingHistory()
    {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetBookingHistory";
    }

    public static String fGetBookingDetailHistory() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetBookingDetailHistory";
    }

    public static String realtime() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetDishQuantities";
    }

    public static String badgeshit() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetBadgeList";
    }

    public static String countrylist() {
        return "http://demosaavorapi.saavor.io/API/UsersService.svc/GetCountriesList";
    }

    public static String fBookingStatusUpdate() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/BookingStatusUpdate";
    }

    public static String ulyxrating() {

        return "http://demosaavorapi.saavor.io/API/UsersService.svc/UlyxOrderReviewInsert";
    }

    public static Object driverdetail() {

        return "http://demosaavorapi.saavor.io/" +
                "API/UsersService.svc/GetUlyxDriverInfo/";
    }
}
