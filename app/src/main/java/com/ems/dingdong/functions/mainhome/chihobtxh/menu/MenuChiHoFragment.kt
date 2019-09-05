package com.ems.dingdong.functions.mainhome.chihobtxh.menu

import android.content.Intent
import android.view.View

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager
import com.core.base.viper.ViewFragment
import com.ems.dingdong.R
import com.ems.dingdong.functions.mainhome.home.HomeGroupAdapter
import com.ems.dingdong.functions.mainhome.phathang.baophatbangke.tabs.ListBaoPhatBangKeActivity
import com.ems.dingdong.functions.mainhome.phathang.baophatkhongthanhcong.BaoPhatBangKhongThanhCongActivity
import com.ems.dingdong.functions.mainhome.phathang.baophatthanhcong.BaoPhatThanhCongActivity
import com.ems.dingdong.functions.mainhome.phathang.thongke.tabs.StatictisActivity
import com.ems.dingdong.model.GroupInfo
import com.ems.dingdong.model.HomeInfo
import com.ems.dingdong.utiles.Constants

import java.util.ArrayList

import butterknife.BindView
import butterknife.OnClick
import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter

/**
 * The PhatHang Fragment
 */
class MenuChiHoFragment : ViewFragment<MenuChiHoContract.Presenter>(), MenuChiHoContract.View {
    @BindView(R.id.recycler)
    lateinit var recycler: RecyclerView
    private var adapter: HomeGroupAdapter? = null
    lateinit var mList: ArrayList<GroupInfo>
    private var mLayoutManager: StickyHeaderGridLayoutManager? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_chi_ho_btxh
    }

    override fun initLayout() {
        super.initLayout()
        mList = ArrayList()

        val homeInfos = ArrayList<HomeInfo>()
        homeInfos.add(HomeInfo(1, R.drawable.ic_btxh_01, "Chi hộ BTXH"))
        homeInfos.add(HomeInfo(2, R.drawable.ic_thong_ke_bao_phat, "Thống kê"))
        mList.add(GroupInfo("BTXH", homeInfos))
        adapter = object : HomeGroupAdapter(mList) {
            override fun onBindItemViewHolder(viewHolder: ItemViewHolder, section: Int, position: Int) {
                super.onBindItemViewHolder(viewHolder, section, position)
                viewHolder.itemView.setOnClickListener {
                    val homeInfo = mList[section].list[position]
                    if (homeInfo.id == 1) {
                        val intent = Intent(activity, ChiHoBtxhActivity::class.java)
                        startActivity(intent)
                    } else if (homeInfo.id == 2) {
                        val intent = Intent(activity, ThongKeBtxhActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        mLayoutManager = StickyHeaderGridLayoutManager(SPAN_SIZE)
        mLayoutManager!!.setHeaderBottomOverlapMargin(resources.getDimensionPixelSize(R.dimen.dimen_8dp))
        recycler!!.itemAnimator = object : DefaultItemAnimator() {
            override fun animateRemove(holder: RecyclerView.ViewHolder): Boolean {
                dispatchRemoveFinished(holder)
                return false
            }
        }
        recycler!!.layoutManager = mLayoutManager
        recycler!!.adapter = adapter
    }

    companion object {
        private val SPAN_SIZE = 2

        val instance: MenuChiHoFragment
            get() = MenuChiHoFragment()
    }

    @OnClick(R.id.img_back)
    fun onViewClicked(view: View) {
        when (view.id) {
            R.id.img_back -> {
                mPresenter.back()
            }
        }
    }
}
